/*
 * Copyright (c) 2013 Joseph Su
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nbcu.medialabs;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.mirror.model.MenuItem;
import com.google.api.services.mirror.model.NotificationConfig;
import com.google.api.services.mirror.model.TimelineItem;

/**
 * Handles timed task for sending timeline cards to users. 
 * The timer was disabled in place of a daemon cron job 
 * 
 * @author Joseph Su - http://google.com/+JosephSu
 */

public class TaskScheduler {
	
	private static final Logger LOG = Logger.getLogger(TaskScheduler.class.getSimpleName());
	private Credential credential = null;

	private ArrayList<String> titleList = new ArrayList<String>();
	private ArrayList<String> descriptionList = new ArrayList<String>();
	private ArrayList<String> urlList = new ArrayList<String>();
	
	public void setListValues(int index, String title, String description, String url) {
		
		titleList.add(index, new String(title));
		descriptionList.add(index, new String(description));
		urlList.add(index, new String(url));
	}
	
	
    public TaskScheduler(Credential credential) {
    	this.credential = credential;
    }

    
    /*
     * TODO: abstract factory to address open-close better
     */    
    public void createContentAndSend(CardContentState type) throws IOException, Exception {
    
	    // Loop through all arraylist entries
	    int index = 0;
	    String title, description;

	    for (String url : urlList) {

	    	TimelineItem timelineItem = new TimelineItem();
		    String contentType = null;
		    InputStream stream = null;

	    	title = titleList.get(index);
	    	description = descriptionList.get(index);
	    	
	    	switch (type) {
	    	
	    		case CNBC_TEMPLATE:
	        		LOG.fine("Inserting CNBC_TEMPLATE");	
	        		LOG.info("Url: " + url);
	        		
//        		String html = 
//	        				"<article><section>"
//	        				+ "<p class='text-x-small'>" 
//	        				+ title
//	        				+ "</p></div></section><footer> "
//	        				+ "<img src='http://5d07f0b14aa094922e3a-1c0bbf3052b29a0fdb9db899cbf59b1d.r37.cf1.rackcdn.com/cnbc-50x50.png' class='left'>"
//	        				+ "<div>CNBC News Alert</div></footer></article>";

        		String html = 
	        				"<article class='photo' style='left: 0px; visibility: visible;'><img src='http://zeeboxwidgets.com/glass/images/sponsor_cnbc.png' style='position:absolute; top:30px; right:20px; z-index:200;'/><div class='overlay-gradient-tall-dark'></div><img src='http://zeeboxwidgets.com/glass/images/cnbc_bg.png' width='100%' height='100%'><section>"
	        				+ "<p class='text-auto-size'>" 
	        				+ title
	        				+ "</p></div></section><footer>"
	        				+ "<img src='http://5d07f0b14aa094922e3a-1c0bbf3052b29a0fdb9db899cbf59b1d.r37.cf1.rackcdn.com/cnbc_45x45.png' class ='left'>"
	        				+ "<div>CNBC News Alert</div></footer></article>";
	        		
	        		timelineItem.setHtml(html);
	        		timelineItem.setSpeakableText(description);
	        		List<MenuItem> menuItemList = new ArrayList<MenuItem>();
					menuItemList.add(new MenuItem().setAction("READ_ALOUD"));
					menuItemList.add(new MenuItem().setAction("OPEN_URI").setPayload(url));
				    menuItemList.add(new MenuItem().setAction("DELETE"));
				    timelineItem.setMenuItems(menuItemList);
	        		timelineItem.setNotification(new NotificationConfig().setLevel("DEFAULT"));        		

	    	    	sendContent(timelineItem, contentType, stream);
	        		break;
	
	    		default: 
	    			break;
        
	    	}
	    	
	    	// TODO: for testing we are popping the first 3 off queue. For commercial application we should always pop the first one	
	    	if (index++ == 2)
	    		return;
	    }
	    
    }
    
    public void sendContent(TimelineItem item, String type, InputStream stream) throws IOException {
    	
    	if (type != null) {
    	    MirrorClient.insertTimelineItem(credential, item, type, stream);
    	} else {
    		MirrorClient.insertTimelineItem(credential, item);	
    	}
    	
    }

}
