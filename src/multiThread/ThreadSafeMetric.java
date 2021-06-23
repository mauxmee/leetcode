package multiThread;

import java.util.concurrent.atomic.AtomicReference;

public class ThreadSafeMetric {
    private static class InternalMetric {
        public long count;
        public long sum;
    }

    // class member stored in heap and shared by all threads
    AtomicReference<InternalMetric> m_internalMetric = new AtomicReference<>(new InternalMetric());

    public void addSample(long sample) {
        // local variables stored in stack, local to this thread.
        InternalMetric currentState;
        InternalMetric newState;
        do {
            currentState = m_internalMetric.get();
            newState = new InternalMetric();
            newState.sum = currentState.sum + sample;
            newState.count = currentState.count + 1;
        } while (!m_internalMetric.compareAndSet(currentState, newState));
    }

    public double getAverage() {
        InternalMetric newResetState = new InternalMetric();
        InternalMetric currentState;
        double average = 0.0;
        do {
            currentState = m_internalMetric.get();
            average = currentState.count == 0 ? 0.0 :
                    (double) currentState.sum / currentState.count;
        } while (!m_internalMetric.compareAndSet(currentState, newResetState));

        return average;
    }
}