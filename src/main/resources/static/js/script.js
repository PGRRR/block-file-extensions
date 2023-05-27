$(document).ready(function () {
    getExtension();
    getExtensionNameList();
    getUploadFileList();

    function getExtension() {
        $.ajax({
            type: 'GET',
            url: '/api/extensions',
            dataType: 'json',
            success: function (result) {
                showStaticExtensions(result);
                showCustomExtensions(result);
            },
            error: function (jqXHR) {
                alert(jqXHR.responseText);
            }
        });
    }

    function showStaticExtensions(result) {
        const staticExtensions = result.filter(function (dto) {
            return dto.isStatic === true;
        }).map(function (dto) {
            return dto.name;
        });

        staticExtensions.forEach(function (staticExtension) {
            const checkbox = $("input[type='checkbox'][value='" + staticExtension + "']");
            checkbox.prop("checked", true);
        });
    }

    function showCustomExtensions(result) {
        const customExtensionsDiv = $("#customExtensions");

        const customExtensions = result.filter(function (dto) {
            return dto.isStatic === false;
        });

        customExtensions.forEach(function (dto) {
            const extensionTag = createExtensionTag(dto.name);
            customExtensionsDiv.append(extensionTag);
        });

        updateExtensionCount();
    }

    $('input[type="checkbox"][name="extension"]').change(function () {
        const extensionValue = $(this).val();
        if ($(this).is(':checked')) {
            addStaticExtensionRequest(extensionValue)
        } else {
            deleteStaticExtension(extensionValue);
        }
    });

    function addStaticExtensionRequest(extensionValue) {
        const data = {
            name: extensionValue,
            isStatic: true
        };

        $.ajax({
            type: 'POST',
            url: '/api/extensions',
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function () {
            },
            error: function (jqXHR) {
                alert(jqXHR.responseText);
            }
        });
    }

    function deleteStaticExtension(extensionValue) {
        $.ajax({
            type: 'DELETE',
            url: '/api/extensions/' + encodeURIComponent(extensionValue),
            success: function () {
            },
            error: function (jqXHR) {
                alert(jqXHR.responseText);
            }
        });
    }

    $('#add').click(function () {
        let customExtensionInput = $('input[name="customExtension"]');
        let value = customExtensionInput.val();
        let pattern = /^[a-zA-Z0-9.]*$/;
        if (!pattern.test(value)) {
            alert('확장자는 영문 대소문자, 숫자 및 마침표만 입력할 수 있습니다.');
            value = value.replace(/[^a-zA-Z0-9.]/g, '');
            customExtensionInput.val(value);
            customExtensionInput.focus();
        } else {
            addCustomExtension();
        }
    });

    function addCustomExtension() {
        const customExtensionInput = $('input[name="customExtension"]').eq(0);
        const extensionValue = customExtensionInput.val().trim();

        if (extensionValue === '') {
            return;
        }

        const checkbox = $("input[name='extension'][value='" + extensionValue + "']");

        if (checkbox.length > 0) {
            if (!checkbox.is(":checked")) {
                const confirmation = confirm("입력한 확장자와 동일한 고정 확장자가 있습니다. 체크하시겠습니까?");
                if (confirmation) {
                    checkbox.prop("checked", true);
                    addStaticExtensionRequest(extensionValue);
                }
            } else {
                alert('이미 추가된 확장자입니다.');
            }
            customExtensionInput.val('');
            customExtensionInput.focus();
            return;
        }
        const isDuplicate = isExtensionDuplicate(extensionValue);

        if (isDuplicate) {
            alert('이미 추가된 확장자입니다.');
            customExtensionInput.focus();
            return;
        }

        const extensionTag = createExtensionTag(extensionValue);
        const customExtensionsDiv = $('#customExtensions');
        customExtensionsDiv.append(extensionTag);
        updateExtensionCount();

        customExtensionInput.val('');
        addCustomExtensionRequest(extensionValue, extensionTag);
    }

    function isExtensionDuplicate(extensionValue) {
        const existingExtensions = $('.extensionTag span');
        return Array.from(existingExtensions).some(function (existingExtension) {
            return $(existingExtension).text().toLowerCase() === extensionValue.toLowerCase();
        });
    }

    function createExtensionTag(extensionValue) {
        const extensionTag = $('<div>').addClass('extensionTag');
        const extensionText = $('<span>').text(extensionValue);
        const deleteButton = $('<button>').text('X').click(function () {
            const extensionTag = $(this).parent();
            const extensionValue = extensionTag.find('span').text();
            deleteCustomExtension(extensionValue, extensionTag);
        });
        extensionTag.append(extensionText);
        extensionTag.append(deleteButton);
        return extensionTag;
    }

    function addCustomExtensionRequest(extensionValue, extensionTag) {
        const data = {
            name: extensionValue,
            isStatic: false
        };

        $.ajax({
            type: 'POST',
            url: '/api/extensions',
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function () {
            },
            error: function (jqXHR) {
                alert(jqXHR.responseText);
                extensionTag.remove();
                updateExtensionCount();
            }
        });
    }

    function deleteCustomExtension(extensionValue, extensionTag) {
        $.ajax({
            type: 'DELETE',
            url: '/api/extensions/' + encodeURIComponent(extensionValue),
            success: function () {
                extensionTag.remove();
                updateExtensionCount();
            },
            error: function (jqXHR) {
                alert(jqXHR.responseText);
            }
        });
    }

    function updateExtensionCount() {
        const extensionTags = $('.extensionTag');
        const extensionCount = $('.extensionCount').eq(0);
        extensionCount.text(extensionTags.length + '/200');
    }

    $('#uploadButton').click(function () {
        var fileInput = $('#fileInput')[0];
        var file = fileInput.files[0];

        var formData = new FormData();
        formData.append('file', file);

        $.ajax({
            url: '/api/files',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function () {
                $('#fileInput').val('');
                alert("업로드 되었습니다.");
                getUploadFileList();
            },
            error: function (jqXHR) {
                $('#fileInput').val('');
                alert(jqXHR.responseText);
            }
        });
    });

    $("#nameListRefreshButton").click(function () {
        getExtensionNameList();
    })

    $("#uploadListRefreshButton").click(function () {
        getUploadFileList();
    })

    function getExtensionNameList() {
        $.ajax({
            url: "/api/extensions/list",
            type: "GET",
            dataType: "json",
            success: function (response) {
                var nameList = $("#nameList ul");
                nameList.empty();
                if (response.length > 0) {
                    response.forEach(function (item) {
                        var listItem = "<li>" + item + "</li>";
                        nameList.append(listItem);
                    });
                } else {
                    var emptyMessage = "<li>테이블 목록이 비어있습니다</li>";
                    nameList.append(emptyMessage);
                }
            },
            error: function (jqXHR) {
                alert(jqXHR.responseText);
            }
        });
    }

    function getUploadFileList() {
        $.ajax({
            url: "/api/files",
            type: "GET",
            dataType: "json",
            success: function (response) {
                var uploadList = $("#uploadList ul");
                uploadList.empty();
                if (response.length > 0) {
                    response.forEach(function (item) {
                        var listItem = "<li>" + item + "</li>";
                        uploadList.append(listItem);
                    });
                } else {
                    var emptyMessage = "<li>파일 목록이 비어있습니다</li>";
                    uploadList.append(emptyMessage);
                }
            },
            error: function (jqXHR) {
                alert(jqXHR.responseText);
            }
        });
    }
});