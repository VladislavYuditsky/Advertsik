<#include "security.ftlh">
<#import "login.ftlh" as l>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="/">Advertsik</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <#if user??>
                <li class="nav-item">
                    <a class="nav-link" href="/new-ad">New ad</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/user-ads/${currentUserId}">My ads</a>
                </li>
            </#if>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/user">User list</a>
                </li>
            </#if>
            <form method="get" action="/" class="form-inline">
                <input class="form-control mr-sm-2" name="filter" type="search" value="${filter!""}" placeholder="Search" aria-label="Search" >
                <button class="btn btn-outline-light my-2 my-sm-0" type="submit">Search</button>
            </form>
        </ul>
        <#if user??>
            <div class="navbar-text mr-3"><a class="nav-link" href="/user/profile">${name}</a></div>
            <@l.logout true/>
        <#else>
            <@l.logout false/>
        </#if>
    </div>
</nav>