let currentElement = null;
let prevPageX = 0;

const offset = {
    state: 0,
    set current(value)  {
        if (value < 0) { value = 0 }
        this.state = value
        getfeed($('input[name=ordertype]:radio').val(), value);
    },
    get current() {
        return this.state
    }
}

function setupGestures() {
    $("li").dblclick(function (e) {
        e.preventDefault();
        currentElement = $(this);
        let kind = currentElement.data("kind");
        currentElement.find("img").fadeOut(200);
        currentElement.find("img").fadeIn(200);
        
        setTimeout(function() {
            vote(kind).upvote(currentElement.data("postid"))
        
        currentElement = null;
        }, 400)
    });
}

const vote = (kind) => {
    if (kind === "post") {
        return {
            upvote: upvote,
        }
    } else {
        return {
            upvote: upvotecom,
        }
    }
}

function buildpost(post) {
    let params = new URLSearchParams(window.location.search);
    let inindex = params.has("postid") ? 0 : 1;
    let commentsurl = `/progetto/html/post/comments.php?postid=${post.postid}`;
    let hastext = (post.text === "") ? 0 : 1;
    let symbol = "❤";
    let callback = upvote;
    let commentsstr = post.comments;
    let likesstr = post.likes;
    commentsstr += ` Comment${(post.comments === 1) ? "" : "s"}`
    likesstr += ` Like${(post.likes === 1) ? "" : "s"}`
    return $("<div>").append([
        $("<li>").append([
            $("<div>").append([
                $("<div>").append([
                    $("<h2>").append(
                        $("<a>").text(post.title).attr("href", commentsurl),
                    ),
                    $("<a>").text(post.userid).attr({
                        href: `/progetto/html/user.php?userid=${post.userid}`,
                        class: "userbutton"
                    }),
                    $("<p>").append([
                        $("<button>").text(symbol).on("click", () => callback(post.postid)),
                        " | ",
                        $("<a>").text(likesstr),
                        " | ",
                        $("<span>").text(post.time),
                        " | ",
                        $("<a>").text(commentsstr).attr({
                            href: commentsurl,
                            class: "datecom"
                        }),
                        " | ",
                        post.mine ? $("<button>").text("Delete").on("click", () => deletepost(post.postid)) : "",
                    ])
                ]),
                $("<div>").append([
                    $("<div>").append([
                        $("<p>").text(post.text)
                    ]).attr("class", "box")
                ]).attr("class", "polaroid"),
            ]).attr("class", "postinfo")
        ]).data("postid", post.postid).data("upvote", post.voted).data("kind", "post")
    ])
}

function deletepost(postid) {
    $.post(
        "/progetto/php/post/delete.php",
        JSON.stringify({ postid: postid }),
        (data) => {
            $(window.location).attr("href", `/progetto/`);
        }
    ).fail(({ responseJSON }) => {
        $("#comments").html($("<p>").text(responseJSON.message));
    });
}

function showError(text) {
    $("main").append($("<div>").text(text).css({
        position: "fixed",
        top: 16,
        right: 16,
        background: "#FEAABB",
        border: "1px solid red",
        borderRadius: 8,
        padding: "16px 32px"
    }).fadeOut(2000));
}

function pager() {
    return $("#pager").append([
        $("<button>").text("◄").on("click", () => { offset.current -= 10 }),
        $("<button>").text("►").on("click", () => { offset.current += 10 })
    ])
}

function getfeed(order, offset) {
    $.get(
        "/progetto/php/feed/feed.php",
        {
            order: order,
            offset: offset
        },
        (data) => {
            if (data.message.length > 0) {
                $("#content").html(buildfeedlayout(data.message))
                setupGestures();
            } else {
                $("#content").html("No more posts.")
            }
        }
    ).fail(({ responseJSON }) => {
        $("#content").html($("<p>").text(responseJSON.message))
    });
}

function buildfeedlayout(feed) {
    return $("<ul>").append(feed.map((post) => buildpost(post)))
}

function getuserfeed(userid, offset) {
    $.get(
        "/progetto/php/user/show.php",
        {
            userid: userid,
            offset: offset
        },
        (data) => {
            if (data.message.length > 0) {
                $("#content").html(buildfeedlayout(data.message))
                setupGestures();
            } else {
                $("#content").html("No more posts.")
            }
        }
    ).fail(({ responseJSON }) => {
        $("#content").html($("<p>").text(responseJSON.message))
    });
}