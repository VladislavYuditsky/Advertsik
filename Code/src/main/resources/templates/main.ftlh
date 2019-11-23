<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/main" class="form-inline">
                <input type="text" name="filter" class="form-control" value="${filter!""}" placeholder="Search by title">
                <button type="submit" class="btn btn-primary ml-2">Search</button>
            </form>
        </div>
    </div>
    <div>
        <form method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input type="text" name="title" class="form-control" placeholder="Введите заголовок"/>
            </div>
            <div class="form-group">
                <input type="text" name="description" class="form-control" placeholder="Описание"/>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file" id="customFile">
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Добавить</button>
            </div>
        </form>
    </div>

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
            </div>
        </div>
    <#else>
        No ads
    </#list>
</@c.page>