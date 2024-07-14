$(document).ready(function () {
    $('#link-profile').addClass('active');
    $('#account-general').show();
    $('#account-change-password').hide();

    $('#link-profile').on('click', function () {
        $('#account-general').show();
        $('#account-change-password').hide();
        $('#link-profile').addClass('active');
        $('#link-pass').removeClass('active');
    });

    $('#link-pass').on('click', function () {
        $('#account-change-password').show();
        $('#account-general').hide();
        $('#link-pass').addClass('active');
        $('#link-profile').removeClass('active');
    });
});