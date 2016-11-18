package com.jdhui.mould;

import com.jdhui.compatibility.ParallelAsyncTask;
import com.jdhui.mould.types.IMouldType;

public abstract class MouldAsyncTask extends
		ParallelAsyncTask<Void, Integer, IMouldType> {

	private String mTaskId;
	private String mType;
	private BaseParams mParams;

	public MouldAsyncTask(String id, BaseParams params) {
		mTaskId = id;
		mParams = params;
		splitType(id);
	}

	private void splitType(String id) {
		if (id != null) {
			String[] array = id.split(":");
			if (array != null && array.length >= 2) {
				mType = array[0];
			}
		}
	}

	@Override
	protected IMouldType doInBackground(Void... params) {
		BaseParams p = mParams;
		if (!isCancelled()) {
			return doTask(p);
		}
		return null;
	}

	public void onPostExecute(IMouldType Result) {
		// MouldRequester.getTaskManager().removeTask(this);
		onPostExecute(Result, mParams);
	}

	protected void onCancelled() {
		// MouldRequester.getTaskManager().removeTask(this);
	}

	public String getTaskType() {
		return this.mType;
	}

	public String getTaskId() {
		return this.mTaskId;
	}

	public abstract IMouldType doTask(BaseParams params);

	public abstract void onPostExecute(IMouldType Result, BaseParams params);
}
