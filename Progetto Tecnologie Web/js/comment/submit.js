$(() => {
  $("form").on("submit", function (e) {
      e.preventDefault();
      const formData = new FormData(e.currentTarget);
      let params = new URLSearchParams(window.location.search);
      let postid = params.get("postid");
      $.post(
        "/progetto/php/comment/submit.php",
        JSON.stringify({...Object.fromEntries(formData), postid: postid}),
        () => {
          getcomments(postid);
          $("#text").val("");
        }
      ).fail(({ responseJSON }) => {
        $("#content").html($("<p>").text(responseJSON.message));
      });
  });
});