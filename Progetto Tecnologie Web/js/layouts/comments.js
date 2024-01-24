$(() => {
  let params = new URLSearchParams(window.location.search);
  if (params.has("postid")) {
      postid = params.get("postid");
      getpost(postid);
      getcomments(postid)
  } else {
      $("#post").html($("<p>").text("postid not found."));
  }
});

function getpost(postid) {
  $.get(
      "/progetto/php/post/show.php",
      { postid: postid },
      (data) => {
          $("#content").html(buildpostlayout(data.message));
          setupGestures();
      }
  ).fail(({ responseJSON }) => {
      $("#content").html($("<p>").text(responseJSON.message));
  });
}

function buildpostlayout(post) {
  return $("<ul>").append(buildpost(post));
}

function upvote(postid) {
  $.post(
      "/progetto/php/post/like.php",
      JSON.stringify({ postid: postid }),
      () => {
          getpost(postid);
      }
  ).fail(({ responseJSON }) => {
      $("#content").html($("<p>").text(responseJSON.message));
  });

}


function getcomments(postid) {
  $.get(
      "/progetto/php/comment/show.php",
      { postid: postid },
      (data) => {
          $("#comments").html(buildcommentslayout(data.message));
          setupGestures();
      }
  ).fail(({ responseJSON }) => {
      $("#comments").html($("<p>").text(responseJSON.message));
  });
}

function buildcommentslayout(comments) {
  return $("<ul>").append(comments.map((comment) => buildcomment(comment)))
}

function buildcomment(comment) {
  let symbol = "❤";
  let callback = upvotecom;
  let likesstr = comment.likes;
  likesstr += ` Like${(comment.likes === 1) ? "" : "s"}`
  return $("<div>").append([
      $("<li>").append([
          $("<div>").append([
              $("<div>").append([
                  $("<p>").append([
                      $("<a>").text(comment.userid).attr({
                          href: `/progetto/html/user.php?userid=${comment.userid}`,
                          class: "userbutton"
                      }),
                      " | ",
                      $("<button>").text(symbol).on("click", () => callback(comment.commentid)).attr("class", "upvote"),
                      " | ",
                      $("<a>").text(likesstr),
                      " | ",
                      $("<span>").text(comment.time),
                      " | ",
                      comment.mine ? $("<button>").text("Delete").on("click", () => deletecomment(comment.commentid)) : "",
                  ])
              ]),
              $("<div>").append([
                  $("<div>").append([
                      $("<a>").text(comment.text)
                  ]),
              ]),
          ]).attr("class", "postinfo")
      ]).data("postid", comment.commentid).data("upvote", comment.voted)
  ])
}

function upvotecom(commentid) {
  $.post(
      "/progetto/php/comment/like.php",
      JSON.stringify({ commentid: commentid }),
      () => {
          getcomments(postid);
      }
  ).fail(({ responseJSON }) => {
      $("#comments").html($("<p>").text(responseJSON.message));
  });

}

function deletecomment(commentid) {
  let params = new URLSearchParams(window.location.search);
  if (params.has("postid")) {
      postid = params.get("postid");
  }
  $.post(
      "/progetto/php/comment/delete.php",
      JSON.stringify({ commentid: commentid }),
      () => {
          getcomments(postid)
      }
  ).fail(({ responseJSON }) => {
      $("#comments").html($("<p>").text(responseJSON.message));
  });
}