/*
 * Based on the Apache License below:
 * 
 * Copyright (C) 2013 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.nbcu.medialabs;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
//import com.google.api.services.mirror.model.Command;
import com.google.api.services.mirror.model.Contact;
import com.google.api.services.mirror.model.NotificationConfig;
import com.google.api.services.mirror.model.Subscription;
import com.google.api.services.mirror.model.TimelineItem;
import com.google.api.services.mirror.model.MenuItem;
import com.google.api.services.mirror.model.MenuValue;
import com.google.common.collect.Lists;
import com.nbcu.medialabs.MainServlet;

import java.io.IOException;
import java.util.logging.Logger;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import javax.servlet.http.HttpServletRequest;

/**
 * Utility functions used when users first authenticate with this service
 *
 * @author Jenny Murphy - http://google.com/+JennyMurphy
 */
public class NewUserBootstrapper {
  private static final Logger LOG = Logger.getLogger(NewUserBootstrapper.class.getSimpleName());

  /**
   * Bootstrap a new user. Do all of the typical actions for a new user:
   * <ul>
   * <li>Creating a timeline subscription</li>
   * <li>Inserting a contact</li>
   * <li>Sending the user a welcome message</li>
   * </ul>
   */
  public static void bootstrapNewUser(HttpServletRequest req, String userId) throws IOException {
    Credential credential = AuthUtil.newAuthorizationCodeFlow().loadCredential(userId);

    // Create contact
    Contact starterProjectContact = new Contact();
    starterProjectContact.setDisplayName(MainServlet.CONTACT_NAME);
    starterProjectContact.setId(MainServlet.CONTACT_ID);
    Contact insertedContact = MirrorClient.insertContact(credential, starterProjectContact);
    
    // Store id and credential in persistent memory store
    AuthUtil.storeUserIdCredential(userId, credential);
    
    LOG.info("Bootstrapper inserted contact " + insertedContact.getId() + " for user " + userId);
   
  }
}
