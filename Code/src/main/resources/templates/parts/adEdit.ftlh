<#macro edit isEditForm>
    <div>
        <form method="post" enctype="multipart/form-data">

            <div class="form-group">
                <input type="text" name="title" class="form-control ${(titleError??)?string('is-invalid','')}"
                       value="<#if ad??>${ad.title}</#if>" placeholder="Введите заголовок"/>
                <#if titleError??>
                    <div class="invalid-feedback">
                        ${titleError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <input type="text" name="description" class="form-control ${(descriptionError??)?string('is-invalid','')}"
                       value="<#if ad??>${ad.description}</#if>" placeholder="Описание"/>
                <#if descriptionError??>
                    <div class="invalid-feedback">
                        descriptionError
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file" id="customFile">
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <#if isEditForm>
                <input type="hidden" name="id" value="<#if ad??>${ad.id}</#if>" />
            </#if>
            <div class="form-group">
                <button type="submit" class="btn btn-dark">Save</button>
            </div>
        </form>
    </div>
</#macro>