package net.customware.gwt.dispatch.client.standard;

import net.customware.gwt.dispatch.client.ExceptionHandler;
import net.customware.gwt.dispatch.shared.counter.IncrementCounter;
import net.customware.gwt.dispatch.shared.counter.IncrementCounterResult;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class GwtTestSpringDispatcher extends GWTTestCase {

    private abstract static class TestCallback<T> implements AsyncCallback<T> {
        public void onFailure( Throwable e ) {
            throw new RuntimeException( e );
        }
    }

    private static final int TEST_DELAY = 500;

    private StandardDispatchAsync dispatch;

    @Override
    public String getModuleName() {
        return "net.customware.gwt.dispatch.SpringDispatchTest";
    }

    @Override
    protected void gwtSetUp() throws Exception {

        dispatch = new StandardDispatchAsync( new ExceptionHandler() {
            public Status onFailure( Throwable e ) {
                throw new RuntimeException( e );
            }
        } );

        super.gwtSetUp();
    }

    @Override
    protected void gwtTearDown() throws Exception {
        dispatch = null;

        super.gwtTearDown();
    }
    
    public void testIncrementCounter() {
        
        dispatch.execute( new IncrementCounter( 1 ), new TestCallback<IncrementCounterResult>() {
            public void onSuccess( IncrementCounterResult result ) {
                assertEquals( 1, result.getCurrent() );
                finishTest();
            }
        } );
        
        // Set a delay period significantly longer than the
        // event is expected to take.
        delayTestFinish( TEST_DELAY );
    }
}
