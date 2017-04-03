package com.ytc.common.result;

import com.ytc.common.model.Model;

public class ModelResult<T extends Model> extends DataResult<T> {
	public ModelResult() {
	}

	public ModelResult(Result r) {
		super(r);
	}

	public ModelResult(ModelResult<T> r) {
		super(r);
	}

	public ModelResult(T data) {
		super(data);
	}

	public ModelResult(ModelResult<?> r, T data) {
		super(r, data);
	}
}
