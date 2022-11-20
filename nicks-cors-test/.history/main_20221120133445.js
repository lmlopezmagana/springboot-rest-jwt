function main() 
{
    console.log("nicks-cors-test");
    $.ajax
    ({
        url: "https://localhost:8080/note/",
        success: function(data) 
        {
            console.log(data);
        }
    });
}
