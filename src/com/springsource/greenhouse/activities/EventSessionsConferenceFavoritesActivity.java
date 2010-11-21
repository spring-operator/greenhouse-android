package com.springsource.greenhouse.activities;

import java.util.List;

import org.springframework.social.greenhouse.Event;
import org.springframework.social.greenhouse.EventSession;

import android.os.AsyncTask;

import com.springsource.greenhouse.controllers.EventSessionsController;
import com.springsource.greenhouse.util.SharedDataManager;

public class EventSessionsConferenceFavoritesActivity extends EventSessionsListActivity {

	
	//***************************************
    // Protected methods
    //***************************************
	@Override
	protected void downloadSessions() {
		new DownloadSessionsTask().execute();
	}
	
	
	//***************************************
    // Private classes
    //***************************************
	private class DownloadSessionsTask extends AsyncTask<Void, Void, List<EventSession>> {
		private Exception mException;
		private EventSessionsController mEventSessionsController;
		private Event mEvent;
				
		@Override
		protected void onPreExecute() {
			showLoadingProgressDialog();
			mEventSessionsController = new EventSessionsController(getContext());
			mEvent = SharedDataManager.getCurrentEvent();
		}
		
		@Override
		protected List<EventSession> doInBackground(Void... params) {
			try {
				if (mEvent == null) {
					return null;
				}
				return mEventSessionsController.getConferenceFavoriteSessions(mEvent.getId());
			} catch(Exception e) {
				logException(e);
				mException = e;
			} 
			
			return null;
		}
		
		@Override
		protected void onPostExecute(List<EventSession> result) {
			dismissProgressDialog();
			processException(mException);
			setSessions(result);
		}
	}
}
