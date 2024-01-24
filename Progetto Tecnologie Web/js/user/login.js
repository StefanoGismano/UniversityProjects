$(() => {
  $("form").on("submit", function (e) {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    if (isvalid_user(Object.fromEntries(formData).username) &&
      isvalid_pass(Object.fromEntries(formData).password.length)) {
      $.post(
        "/progetto/php/user/login.php",
        JSON.stringify(Object.fromEntries(formData)),
        (data) => {
          $(window.location).attr("href", `/progetto/`);
        }
      ).fail(({ responseJSON }) => {
        $("#content").html($("<p>").text(responseJSON.message));
      });
    } else {
      return $("#content").html($("<p>").text(`Username should be
            alphanumeric and between 3 and 20 characters. Password should
            be at least 8 characters long.`));
    }
  });
});

function isvalid_user(username) {
  const userlen = username.length;
  const alnum = /^([a-zA-Z0-9\u0600-\u06FF\u0660-\u0669\u06F0-\u06F9 _.-]+)$/
  if (userlen >= 3 && userlen < 21 && username.match(alnum)) {
    return true;
  }
  return false;
}

function isvalid_pass(passlen) {
  return passlen >= 8 ? true : false
}