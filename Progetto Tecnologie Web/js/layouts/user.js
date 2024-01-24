let user;

const useroffset = {
    state: 0,
    set current(value)  {
        if (value < 0) { value = 0 }
        this.state = value
        getuserfeed(user, value);
    },
    get current() {
        return this.state
    }
}

$(() => {
        let params = new URLSearchParams(window.location.search);
        user = params.get("userid");
        getuserfeed(user, useroffset.current);
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