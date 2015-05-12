/*
 * Copyright (c) 2013 NBCUniversal, Inc
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.mirror.model.TimelineItem;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;


/* 
 * Generic scrapper for fetch and trigger Glass time card when news is available. Will need to 
 * be refactored to support notification through messaging bus in future 
 * @author Joseph Su
 */
public class NewsGenerator {
	
	private static final Logger LOG = Logger.getLogger(NewsGenerator.class.getSimpleName());
	private static String CNBC_FEED_URL = "http://search.cnbc.com/main.do?partnerId=20048";

	
	protected static void triggerCNBCNews(Credential credential) throws IOException {

		// Let's delete all previously inserted timeline items
		List<TimelineItem> timelineItems = MirrorClient.listItems(credential, 3L).getItems();
		
		if (timelineItems != null && !timelineItems.isEmpty()) {
	        for (TimelineItem timelineItem : timelineItems) {
	        	MirrorClient.deleteTimelineItem(credential, timelineItem.getId());
	        }
		}
		
		try {
			
			parseRss(credential);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}  
	
	private static void parseRss(Credential credential) throws Exception {

		String html = null;
		String url;
		String title;
		String description;
		
		TaskScheduler scheduler = new TaskScheduler(credential);
		
		SyndFeed feed = getFeed();
		
		if (feed == null)
			return;
			
		int index = 0;
		for (Iterator j=feed.getEntries().iterator(); j.hasNext();) {
        
        	SyndEntry entry = (SyndEntry)j.next();
        	title = entry.getTitle();
        	url = entry.getUri();
        	description = entry.getDescription().getValue();        	
    		scheduler.setListValues(index, title, description, url);
        	index++;
        	
		} // end for Iterator loop
		
		scheduler.createContentAndSend(CardContentState.CNBC_TEMPLATE);
		
		
		
  	}
	
	private static SyndFeed getFeed() throws Exception {
		
		SyndFeed feed = null;
		
		URL url = new URL(CNBC_FEED_URL);  
		HttpURLConnection httpcon = (HttpURLConnection)url.openConnection();  
		SyndFeedInput input = new SyndFeedInput();  
		feed = input.build(new XmlReader(httpcon));  

		return feed;
	}	
}
