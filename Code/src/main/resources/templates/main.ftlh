<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <div>
        <@l.logout />
    </div>
    <div>
        <form method="post">
            <input type="text" name="title" placeholder="Введите заголовок"/>
            <input type="text" name="description" placeholder="Описание"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit">Добавить</button>
        </form>
    </div>
    <div>Список объявлений</div>
    <form method="get" action="/main">
        <input type="text" name="filter" value="${filter!""}">
        <button type="submit">Найти</button>
    </form>
    <#list ads as ad>
        <div>
            <i>${ad.title}</i>
            <span>${ad.description}</span>
            <b>${ad.id}</b>
            <strong>${ad.authorName}</strong>
        </div>
    <#else>
        No ads
    </#list>
</@c.page>