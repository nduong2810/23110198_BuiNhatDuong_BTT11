// Trang profile: load thông tin user
$(document).ready(function () {
  if (location.pathname === '/user/profile') {
    $.ajax({
      type: 'GET',
      url: '/users/me',
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      beforeSend: function (xhr) {
        if (localStorage.token) {
          xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
        }
      },
      success: function (data) {
        // data fullName, images (tên file nằm trong /images)
        $('#profile').text(data.fullName);
        if (data.images) {
          $('#images').attr('src', '/images/' + data.images);
        }
      },
      error: function (e) {
        $('#profile').text('');
        alert('Sorry, you are not logged in.');
        window.location.href = '/login';
      }
    });
  }
});

// Nút Login (trang login)
$('#Login').on('click', function () {
  var email = $('#email').val();
  var password = $('#password').val();
  $.ajax({
    type: 'POST',
    url: '/auth/login',
    dataType: 'json',
    contentType: 'application/json; charset=utf-8',
    data: JSON.stringify({ email: email, password: password }),
    success: function (data) {
      localStorage.token = data.token;      // lưu JWT
      window.location.href = '/user/profile';
    },
    error: function () {
      $('#feedback').text('Login Failed');
    }
  });
});

// Nút Logout (trang profile)
$('#Logout').on('click', function () {
  localStorage.clear();
  window.location.href = '/login';
});
