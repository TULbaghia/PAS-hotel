$("body").on("click", ".show-ok-alert", (event) => {
    let response = confirm("Are you sure?");
    if (!response) {
        event.preventDefault();
    }
})

$("body").on("change", ".js-how-many-beds, .js-door-number", (event) => {
    checkCorrectness(event, /^[1-9][0-9]*$/, "Wartość musi być większa od zera");
})

$("body").on("change", ".js-base-price", (event) => {
    checkCorrectness(event, /^\d*\.?\d{1,2}$/, "Niepoprawna wartość");
})

$("body").on("change", ".js-pc-name, .js-bonus, .js-login, .js-password, .js-firstname, .js-lastname", (event) => {
    checkCorrectness(event, /^[a-zA-Z0-9]+[a-zA-Z0-9\s]*$/, "Pole nie może być puste");
})

$("body").on("change", ".js-reservation-start-date", (event) => {
    checkCorrectness(event, /^\d{4}-[01]\d-[0-3]\dT[0-2]\d:[0-5]\d:[0-5]\d$/, "Niepoprawna data");
})

$("body").on("change", ".js-password", (event) => {
    checkCorrectness(event, /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/,
        "Hasło musi zawierać minimum 1 znak specjalny, 1 małą literę, 1 dużą literę oraz cyfrę. Minimum 8 znaków."
        );
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

// styleClass="js-how-many-beds"
// styleClass="js-door-number"
// styleClass="js-base-price"
// styleClass="js-bonus"
// styleClass="js-pc-name