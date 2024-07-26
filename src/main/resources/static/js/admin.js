$(document).ready(function () {
    var isCurrentLogin = $('#currentLogin').val();

    function saveRole() {
        $('.role-select').change(function () {
            let userId = $(this).attr("data-id");
            let roleName = $(this).val();
            let params = new window.URLSearchParams(window.location.search);
            let currentPage;
            let search = $('#searchText').val();
            if (params.get("page") == null) {
                currentPage = 1;
            } else {
                currentPage = params.get("page");
            }
            let requestSaveUserRoleDto = {
                userId: userId,
                roleName: roleName,
                page: currentPage,
                search: search
            };
            $.ajax({
                url: "/save-role/",
                type: 'POST',
                data: JSON.stringify(requestSaveUserRoleDto),
                contentType: 'application/json; charset=utf-8',
                error: function (error) {
                    console.error(error.data)
                },
                success: function (data) {
                    $('#table').empty();
                    $('.role-select').empty();
                    $.each(data.resultList, function (key, value) {
                        $('#table').append(`
                        <tr>
                           <td>${value.userId}</td>
                           <td>${value.username}</td>
                           <td><select class="role-select" data-id="${value.userId}" ${value.username == isCurrentLogin ? 'disabled' : ''}>
                                <option value="${data.roles[0].roleName}" ${data.roles[0].roleName === value.roles[0].roleName ? 'selected' : ''}>${data.roles[0].roleName}</option>
                                <option value="${data.roles[1].roleName}" ${data.roles[1].roleName === value.roles[0].roleName ? 'selected' : ''}>${data.roles[1].roleName}</option>
                                <option value="${data.roles[2].roleName}" ${data.roles[2].roleName === value.roles[0].roleName ? 'selected' : ''}>${data.roles[2].roleName}</option>
                           </select></td>
                           <td><a class="btn btn-info" href="/user_detail/${value.userId}">Details</a></td>
                           <td>
                                <button class="btn btnChange ${value.status === 'ACTIVE' ? 'btn-success' : 'btn-danger'}" data-id="${value.userId}" value="${value.status}" ${value.username == isCurrentLogin ? 'disabled' : ''}>${value.status}</button>
                           </td>
                        </tr>
                    `);
                    });
                },
                complete: function () {
                    saveRole();
                    saveActive();
                    search();
                }
            })
        });
    }

    saveRole();

    function saveActive() {
        $('.btnChange').on('click', function () {
            let userId = $(this).attr("data-id");
            let status = $(this).val();
            let params = new window.URLSearchParams(window.location.search);
            let currentPage;
            var search = $('#searchText').val();
            if (params.get("page") == null) {
                currentPage = 1;
            } else {
                currentPage = params.get("page");
            }
            let requestSaveActiveDto = {
                userId: userId,
                status: status,
                page: currentPage,
                search
            };
            $.ajax({
                url: "/save-active/",
                type: 'POST',
                data: JSON.stringify(requestSaveActiveDto),
                contentType: 'application/json; charset=utf-8',
                error: function (error) {
                    console.error(error.data)
                },
                success: function (data) {
                    $('#table').empty();
                    $.each(data.resultList, function (key, value) {
                        $('#table').append(`
                        <tr>
                           <td>${value.userId}</td>
                           <td>${value.username}</td>
                           <td><select class="role-select" data-id="${value.userId}" ${value.username == isCurrentLogin ? 'disabled' : ''}>
                                <option value="${data.roles[0].roleName}" ${data.roles[0].roleName === value.roles[0].roleName ? 'selected' : ''}>${data.roles[0].roleName}</option>
                                <option value="${data.roles[1].roleName}" ${data.roles[1].roleName === value.roles[0].roleName ? 'selected' : ''}>${data.roles[1].roleName}</option>
                                <option value="${data.roles[2].roleName}" ${data.roles[2].roleName === value.roles[0].roleName ? 'selected' : ''}>${data.roles[2].roleName}</option>
                           </select></td>
                           <td><a class="btn btn-info" href="/user_detail/${value.userId}">Details</a></td>
                           <td>
                                <button class="btn btnChange ${value.status === 'ACTIVE' ? 'btn-success' : 'btn-danger'}" data-id="${value.userId}" value="${value.status}" ${value.username == isCurrentLogin ? 'disabled' : ''}>${value.status}</button>
                           </td>
                        </tr>
                    `);
                    });
                },
                complete: function () {
                    saveActive();
                    saveRole();
                    search();
                }
            })
        });
    }

    saveActive();

    var timer;
    var timeout = 1000;

    function search() {
        $('#searchText').keyup(function () {
            let params = new window.URLSearchParams(window.location.search);
            let currentPage;
            if (params.get("page") == null) {
                currentPage = 1;
            } else {
                currentPage = params.get("page");
            }
            let searchText = $(this).val();
            let requestSearchUserDto = {
                currentPage: currentPage,
                search: searchText
            };
            clearTimeout(timer);
            if ($('#searchText').val()) {
                timer = setTimeout(function () {
                    //do stuff here e.g ajax call etc....
                    $.ajax({
                        url: "/search",
                        type: 'POST',
                        data: JSON.stringify(requestSearchUserDto),
                        contentType: 'application/json; charset=utf-8',
                        error: function (error) {
                            console.error(error.data)
                        },
                        success: function (data) {
                            $('#table').empty();
                            $('.role-select').empty();
                            $.each(data.resultList, function (key, value) {
                                $('#table').append(`
                        <tr>
                           <td>${value.userId}</td>
                           <td>${value.username}</td>
                           <td><select class="role-select" data-id="${value.userId}" ${value.username == isCurrentLogin ? 'disabled' : ''}>
                                <option value="${data.roles[0].roleName}" ${data.roles[0].roleName === value.roles[0].roleName ? 'selected' : ''}>${data.roles[0].roleName}</option>
                                <option value="${data.roles[1].roleName}" ${data.roles[1].roleName === value.roles[0].roleName ? 'selected' : ''}>${data.roles[1].roleName}</option>
                                <option value="${data.roles[2].roleName}" ${data.roles[2].roleName === value.roles[0].roleName ? 'selected' : ''}>${data.roles[2].roleName}</option>
                           </select></td>
                           <td><a class="btn btn-info" href="/user_detail/${value.userId}">Details</a></td>
                           <td>
                                <button class="btn btnChange ${value.status === 'ACTIVE' ? 'btn-success' : 'btn-danger'}" data-id="${value.userId}" value="${value.status}" ${value.username == isCurrentLogin ? 'disabled' : ''}>${value.status}</button>
                           </td>
                        </tr>
                    `);
                            });
                        },
                        complete: function () {
                            saveRole();
                            saveActive();
                            search();
                        }
                    })
                }, timeout);
            }
        })
    }

    search();
});