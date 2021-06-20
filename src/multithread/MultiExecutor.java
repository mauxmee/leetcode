package multithread;

import java.util.List;

public class MultiExecutor
{
	private List<Runnable> m_runnableList;

	public MultiExecutor( final List<Runnable> runnableList )
	{
		m_runnableList = runnableList;
	}

	public void executeAll()
	{
		for ( Runnable r : m_runnableList )
		{
			Thread thread = new Thread( r );
			thread.start();
		}
	}
}
