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
                <div class="row">
                    <div class="col">
                        ${ad.authorName}
                    </div>
                    <#if ad.author.id == currentUserId && isUserListForm>
                        <form method="post" action="/user-ads/${ad.author.id}/${ad.id}">
                            <div class="col">
                                <a class="btn btn-dark" href="/ad-editor/?ad=${ad.id}">
                                    Edit
                                </a>
                                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                                <button type="submit" class="btn btn-dark">Delete</button>
                            </div>
                        </form>
                    </#if>
                </div>
            </div>
        </div>
    <#else>
        No ads
    </#list>
</#macro>