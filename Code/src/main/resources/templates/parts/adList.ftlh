<#include "security.ftlh">

<#macro adList isUserListForm>
    <div class="card-columns">
    <#list ads as ad>
        <form method="post" action="/user-ads/${ad.authorId}/${ad.id}">
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
                        <div class="col-md-6">
                            ${ad.authorName}
                        </div>
                        <#if ad.author.id == currentUserId && isUserListForm>
                            <div class="col-md-2 align-self-end">
                                <a class="btn btn-dark" href="/ad-editor/?ad=${ad.id}">
                                    Edit
                                </a>
                            </div>
                            <div class="col-md-2">
                                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                                <button type="submit" class="btn btn-dark">Delete</button>
                            </div>
                        </#if>
                    </div>
                </div>
            </div>
        </form>
    <#else>
        No ads
    </#list>
</#macro>