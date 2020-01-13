package com.quodai.githubmetric.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "public" })
public class GithubEvent {
	
	private String id;
	private String type;
	private GithubActor actor;
	private GithubRepo repo;
	private GithubPayload payload;
	private String created_at;
	private GithubOrganisation org;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public GithubActor getActor() {
		return actor;
	}
	public void setActor(GithubActor actor) {
		this.actor = actor;
	}
	public GithubRepo getRepo() {
		return repo;
	}
	public void setRepo(GithubRepo repo) {
		this.repo = repo;
	}
	public GithubPayload getPayload() {
		return payload;
	}
	public void setPayload(GithubPayload payload) {
		this.payload = payload;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public GithubOrganisation getOrg() {
		return org;
	}
	public void setOrg(GithubOrganisation org) {
		this.org = org;
	}
	
}
