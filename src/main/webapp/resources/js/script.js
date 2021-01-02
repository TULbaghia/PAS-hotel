$("body").on("click", ".show-ok-alert", (event) => {
    let response = confirm(jsMap.JS_confirm);
    if (!response) {
        event.preventDefault();
    }
})

$("body").on("change", ".js-how-many-beds, .js-door-number", (event) => {
    checkCorrectness(event, /^[1-9][0-9]*$/, jsMap.JS_positive);
})

$("body").on("change", ".js-base-price", (event) => {
    checkCorrectness(event, /^\d*\.?\d{1,2}$/, jsMap.JS_incorrect_value);
})

$("body").on("change", ".js-pc-name, .js-bonus, .js-login, .js-password, .js-firstname, .js-lastname", (event) => {
    checkCorrectness(event, /^[a-zA-Z0-9]+[a-zA-Z0-9\s]*$/, jsMap.JS_field_empty);
})

$("body").on("change", ".js-reservation-start-date", (event) => {
    checkCorrectness(event, /^\d{4}-[01]\d-[0-3]\dT[0-2]\d:[0-5]\d:[0-5]\d$/, jsMap.JS_incorrect_date);
})

$("body").on("change", ".js-password", (event) => {
    checkCorrectness(event, /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/, jsMap.JS_password);
})

$("body").on("change", ".js-filter", (event) => {
    $(event.target).closest("form").first().submit();
})

$("body").on("submit", "#filterForm", (e) => {
    e.preventDefault();
    let form = $(e.target);
    let submit = form.find("[type='submit']");
    let formData = form.serializeArray();
    formData.push({name: submit.attr("name"), value: submit.val()});

    $.ajax({
        type: form.attr("method"),
        url: form.attr("action"),
        data: formData,
        success: (data) => {
            let page = $(data);
            let filteredTable = page.find("#filterContent");
            $("#filterContent").html(filteredTable);
        }
    });
});

$(document).ready(() => {
    $(".dispatch-on-load input:not([type='submit'])").each((i, x) => {
        $(x).trigger("change");
    });
});

function handleSubmitEditButton(event) {
    let form = $(event.target).closest("form");
    let submitEdit = form.find("[type='submit']").first();
    if (form.find(".text-danger").length) {
        submitEdit.prop("disabled", true);
    } else {
        submitEdit.prop("disabled", false);
    }
}

function checkCorrectness(event, regex, info) {
    let classes = $(event.target).attr("class").replace('/ /gi', '').replace('/./gi', '_') + "Cos";
    $('.' + classes).remove();
    let value = event.target.value;
    if (!regex.test(value)) {
        $(event.target).after('<span class="' + classes + ' text-danger"><br/>' + info + '</span>');
    }
    handleSubmitEditButton(event);
}