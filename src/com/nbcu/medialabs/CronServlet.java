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
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.Credential;

/**
 * Handles cron requests and processing of response. 
 * This class works under the premise of a persistent 
 * Google auth credential store
 * 
 * @author Joseph Su - http://google.com/+JosephSu
 */

public class CronServlet extends HttpServlet {

	/**
	 * default serialization 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(CronServlet.class.getSimpleName());


	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
		
		    List<String> users = AuthUtil.getAllUserIds();
		    for (String user : users) {
		          Credential userCredential = AuthUtil.getCredential(user);
		          NewsGenerator.triggerCNBCNews(userCredential);
		    }
		} catch (Exception ex) {
			LOG.info(ex.toString());
			LOG.info(ex.getMessage());
			ex.printStackTrace();		
		}
		
		resp.setContentType("text/html");
	 	resp.setStatus(HttpServletResponse.SC_OK);
	 	return;
	}


	

}
