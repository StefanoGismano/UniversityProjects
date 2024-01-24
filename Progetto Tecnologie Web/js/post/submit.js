$(() => {
  $("form").on("submit", function (e) {
      e.preventDefault();
      const formData = new FormData(e.currentTarget);
      if (Object.fromEntries(formData).title.length < 81) {
          $.ajax({
              url: "/progetto/php/post/submit.php",
              type: "POST",
              data: formData,
              contentType: false,
              cache: false,
              processData: false,
              success: function (data) {
                   $(window.location).attr("href", `/progetto/html/post/comments.php?postid=${data.message}`);
              },
              error: function (responseJSON) {
                  return showError("Error submitting post.");
              }
          });
      } else {
          return showError("Error submitting post.");
      }
  });
});