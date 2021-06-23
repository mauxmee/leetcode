package multiThread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.LinkedBlockingQueue;

public class ChainOfResponsibility {
    public static abstract class Handler extends Thread {
        private final int rank;
        private Handler nextHandler;

        LinkedBlockingQueue<Customer> customers = new LinkedBlockingQueue<>();

        public Handler(int rank) {
            this.rank = rank;
            start();
        }

        public Handler getNextHandler() {
            return nextHandler;
        }

        public void setNextHandler(Handler nextHandler) {
            this.nextHandler = nextHandler;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Customer c = customers.take();
                    if (c.getRank() >= rank) {
                        handleHook(c);
                    }
                    if (nextHandler != null) {
                        nextHandler.addCustomer(c);
                    }
                } catch (InterruptedException e) {
                    return;
                }
            }
        }

        public void addCustomer(Customer c) {
            customers.offer(c);
        }

        protected abstract void handleHook(Customer c);

        public static void stop(Handler handler) {
            handler.interrupt();
            if (handler.getNextHandler() != null) {
                stop(handler.getNextHandler());
            }
        }
    }

    public static class FirstHandler extends Handler {
        public FirstHandler() {
            super(1);
            setNextHandler(new SecondHandler());
        }

        @Override
        protected void handleHook(Customer c) {
            System.out.println("P1 VIP level " + c.getRank() + ", " + c.getName() +
                    " price after discount: " + c.getConsumption() * 0.7);
        }
    }

    public static class SecondHandler extends Handler {
        public SecondHandler() {
            super(2);
        }

        @Override
        protected void handleHook(Customer c) {
            double consumption = c.getConsumption() - Math.floor(c.getConsumption() / 100) * 7;
            System.out.println("P2 VIP level " + c.getRank() + ", " + c.getName() +
                    " price after discount: " + consumption);
        }
    }

    public static class Customer {
        private int rank;
        private String name;
        private double consumption;

        public Customer(int rank, String name, double consumption) {
            this.rank = rank;
            this.name = name;
            this.consumption = consumption;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getConsumption() {
            return consumption;
        }

        public void setConsumption(double consumption) {
            this.consumption = consumption;
        }
    }

    @Test
    public void testChainOfResponsibleLogic() throws InterruptedException {
        Customer bob = new Customer(2, "Bob", 1000);
        Customer tom = new Customer(2, "Tom", 1200);
        FirstHandler handler = new FirstHandler();
        handler.addCustomer(bob);
        handler.addCustomer(tom);
        Thread.sleep(5000);
        Handler.stop(handler);

    }
}