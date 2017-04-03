package com.ytc.common.result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Provides the results of an API method call.
 */
public class ListResult<T> extends DataResult<List<T>> {
    /**
     * The total number of objects returned.
     * @APIDOCRequiredParam
     */
    private int total;

    public ListResult() {
        this.data = new ArrayList<T>();
    }

    public ListResult(Result r) {
        super(r);
        this.data = new ArrayList<T>();
    }

    public ListResult(ListResult<T> r) {
        super(r);
        this.data = new ArrayList<T>(r.data);
    }

    public ListResult(Collection<T> list) {
        this.data = new ArrayList<T>(list);
        this.total = list.size();
    }

    public ListResult(DataResult<?> r, List<T> data) {
        super(r, data);
    }

    @Override
    public String toString() {
        return super.toString() + "; ListSize=" + data.size();
    }

    /**
     * Total size of this list (if pagination was not applied).
     */
    public int getTotal() {
        return total;
    }

    public void setTotal(int totalSize) {
        this.total = totalSize;
    }
}
