function main() 
{
    console.log("nicks-cors-test");
    $.ajax
    ({
        url: "http://localhost:8080/note/",
        method: "GET",
        success: function(data) 
        {
            console.log(data);
        }
    });
}
