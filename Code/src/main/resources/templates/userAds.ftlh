<#import "parts/common.ftlh" as c>

<@c.page>
    <#if isCurrentUser>
        <#include "parts/adEdit.ftlh" />
    </#if>
    <#include "parts/adList.ftlh" />

</@c.page>