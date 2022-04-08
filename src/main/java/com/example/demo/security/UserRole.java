package com.example.demo.security;

import static com.example.demo.security.UserPermissions.*;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {
	ADMIN(Set.of(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE)),
	STUDENT(Set.of(STUDENT_READ, STUDENT_WRITE));

	private final Set<UserPermissions> permissions;

	UserRole(Set<UserPermissions> permissions) {
		this.permissions = permissions;
	}

	public Set<UserPermissions> getPermissions() {
		return permissions;
	}

	public Set<GrantedAuthority> getGrantedAuthorities() {
		Set<GrantedAuthority> authorities = getPermissions()
				.stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
				.collect(Collectors.toSet());

		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return authorities;
	}

}
