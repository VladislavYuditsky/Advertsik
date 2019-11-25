<#include "security.ftlh">

<#macro adList isUserListForm>
    <div class="card-columns">
    <#list ads as ad>
        <div class="card my-3">
            <#if user??>
                <#if ad.filename??>
                    <img src="/img/${ad.filename}" class="card-img-top">
                </#if>
            </#if>
            <div class="m-2">
                <i>${ad.title}</i>
                <span>${ad.description}</span>
            </div>
            <div class="card-footer text-muted">
                ${ad.authorName}
                <#if ad.author.id == currentUserId && isUserListForm>
                    <a class="btn btn-dark" href="/ad-editor/?ad=${ad.id}">
                        Edit
                    </a>
                    <button type="submit" class="btn btn-dark">Delete</button>
                </#if>
            </div>
        </div>
    <#else>
        No ads
    </#list>
</#macro>