$(() => {
  getfeed("new", offset.current)
  $('input[name=ordertype]:radio').on("change", function (event) {
      getfeed($(this).val(), offset.current)
  });
  pager();
});

function upvote(postid) {
  $.post(
      "/progetto/php/post/like.php",
      JSON.stringify({ postid: postid }),
      () => {
          getfeed($('input[name=ordertype]:radio').val(), offset.current);
      }
  ).fail(({ responseJSON }) => {
      showError(responseJSON.message)
  });
}
