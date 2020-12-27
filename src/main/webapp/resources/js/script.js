$("body").on("click", ".show-ok-alert", (event) => {
    let response = confirm("Are you sure?");
    if (!response) {
        event.preventDefault();
    }
})

$("body").on("change", ".js-how-many-beds, .js-door-number", (event) => {
    let classes = $(event.target).attr("class").replace('/ /gi', '').replace('/./gi', '_') + "Cos";
    $('.' + classes).remove();
    let value = event.target.value;
    if (!(/^[1-9][0-9]*$/.test(value))) {
        $(event.target).after('<span class="' + classes + ' text-danger"><br/>Niepoprawna wartość</span>');
    }
})

$("body").on("change", ".js-base-price", (event) => {
    let classes = $(event.target).attr("class").replace('/ /gi', '').replace('/./gi', '_') + "Cos";
    $('.' + classes).remove();
    let value = event.target.value;

    if (!(/^\d*\.?\d{1,2}$/.test(value))) {
        $(event.target).after('<span class="' + classes + ' text-danger"><br/>Niepoprawna wartość</span>');
    }
})

// styleClass="js-how-many-beds"
// styleClass="js-door-number"
// styleClass="js-base-price"
// styleClass="js-bonus"
// styleClass="js-pc-name