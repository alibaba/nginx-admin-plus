package com.jslsolucoes.nginx.admin.controller;

import javax.inject.Inject;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;
import com.jslsolucoes.nginx.admin.util.HtmlUtil;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Controller
@Path("nginx")
public class NginxController {

	private Result result;
	private NginxRepository nginxRepository;

	public NginxController() {

	}

	@Inject
	public NginxController(Result result, NginxRepository nginxRepository) {
		this.result = result;
		this.nginxRepository = nginxRepository;
	}

	public void validate(Long id, String bin, String home) {
		this.result.use(Results.json())
				.from(HtmlUtil.convertToUnodernedList(nginxRepository.validateBeforeUpdate(new Nginx(id, bin, home))),
						"errors")
				.serialize();
	}

	public void edit() {
		this.result.include("nginx", this.nginxRepository.nginx());
	}

	public void update(Long id, String bin, String home) {
		this.nginxRepository.update(new Nginx(id, bin, home));
		this.result.include("updated", true);
		this.result.redirectTo(this).edit();
	}

}
