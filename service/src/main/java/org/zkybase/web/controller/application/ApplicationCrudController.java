/* 
 * ApplicationNoFormController.java
 * 
 * Copyright 2011-2012 the original author or authors.
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
package org.zkybase.web.controller.application;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zkybase.model.Application;
import org.zkybase.model.Farm;
import org.zkybase.service.ApplicationService;
import org.zkybase.service.CIService;
import org.zkybase.service.FarmService;
import org.zkybase.util.CollectionsUtil;
import org.zkybase.web.controller.AbstractCrudController;

/**
 * Application CRUD controller.
 * 
 * @author Willie Wheeler (willie.wheeler@gmail.com)
 */
@Controller
@RequestMapping("/applications")
public class ApplicationCrudController extends AbstractCrudController<Application> {
	private static final String[] ALLOWED_FIELDS = new String[] {
		"name", "shortDescription", "scm.user", "scm.repo"
	};
	
	@Inject private ApplicationService applicationService;
	@Inject private FarmService farmService;
	
	/* (non-Javadoc)
	 * @see org.zkybase.web.controller.AbstractCrudController#getService()
	 */
	@Override
	public CIService<Application> getService() { return applicationService; }
	
	/* (non-Javadoc)
	 * @see org.zkybase.web.controller.AbstractCrudController#getAllowedFields()
	 */
	@Override
	protected String[] getAllowedFields() { return ALLOWED_FIELDS; }
	
	/* (non-Javadoc)
	 * @see org.zkybase.web.controller.AbstractEntityNoFormController#doGetDetails(java.lang.Long, org.springframework.ui.Model)
	 */
	@Override
	protected Application doGetDetails(Long id, Model model) {
		Application app = getService().findOne(id);
		List<Farm> farms = CollectionsUtil.asSortedList(farmService.findByApplication(app));
		model.addAttribute(farms);
		return app;
	}
}
