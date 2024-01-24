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
    $("form").on("submit", function (e) {
        e.preventDefault();
        const formData = new FormData(e.currentTarget);
        user = Object.fromEntries(formData).username;
        getuserfeed(user, useroffset.current);
        pager();
    });
});

function upvote(postid) {
    $.post(
        "/progetto/php/post/like.php",
        JSON.stringify({ postid: postid }),
        () => {
            getuserfeed(user, useroffset.current);
        }
    ).fail(({ responseJSON }) => {
        showError(responseJSON.message)
    });
}