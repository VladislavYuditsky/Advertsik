<#include "security.ftlh">

<div class="card-columns">
    <#list ads as ad>
    <div class="card my-3">
        <#if ad.filename??>
            <img src="/img/${ad.filename}" class="card-img-top">
        </#if>
        <div class="m-2">
            <i>${ad.title}</i>
            <span>${ad.description}</span>
        </div>
        <div class="card-footer text-muted">
            ${ad.authorName}
            <#if ad.author.id == currentUserId>
                <a class="btn btn-dark" href="/user-ads/${ad.author.id}?ad=${ad.id}">
                    Edit
                </a>
            </#if>

        </div>
    </div>
    <#else>
    No ads
</#list>