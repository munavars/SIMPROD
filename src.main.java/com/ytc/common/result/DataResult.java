package com.ytc.common.result;

/** 
 * Provides the results of an API method call.
 */
public class DataResult<T> extends Result {
    /**
     * The object(s) that were affected by the API method call.
     * @APIDOCRequiredParam 
     */
    protected T data;

    public DataResult() {
        super();
    }

    public DataResult(Result r) {
        super(r);
    }

    public DataResult(DataResult<T> r) {
        super(r);
        this.data = r.data;
    }

    public DataResult(T data) {
        super();
        this.data = data;
    }

    public DataResult(DataResult<?> r, T data) {
        this(data);
        this.setClientRequestId(r.getClientRequestId());
        this.setResultCode(r.getResultCode());
        this.setResultSubCode(r.getResultSubCode());
        this.setServiceTransactionId(r.getServiceTransactionId());
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return super.toString() + "; Data=" + data;
    }
}
