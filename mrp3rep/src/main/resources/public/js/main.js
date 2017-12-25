// Here we'll be used AngularJS framework and Java Freemarker template manager
const SPACE = " ";
let app = angular.module('mrp3', []);

// Here is the main controller of index page
app.controller("AppCtrl00", function ($scope, $http) {

    $http.get('/now').then(function (response) {

        let txtMonth = getRussianMonth(response.data.month);
        // Build the response for the header of index page
        $scope.now = SPACE + txtMonth + SPACE + response.data.year;

    });

});

// Translate month names
function getRussianMonth(month) {

    let txt = '';

    switch (month) {
        case 'JANUARY':
            txt = 'января';
            break;
        case 'FEBRUARY':
            txt = 'февраля';
            break;
        case 'MARCH':
            txt = 'марта';
            break;
        case 'APRIL':
            txt = 'апреля';
            break;
        case 'MAY':
            txt = 'мая';
            break;
        case 'JUNE':
            txt = 'июня';
            break;
        case 'JULY':
            txt = 'июля';
            break;
        case 'AUGUST':
            txt = 'августа';
            break;
        case 'SEPTEMBER':
            txt = 'сентября';
            break;
        case 'OCTOBER':
            txt = 'октября';
            break;
        case 'NOVEMBER':
            txt = 'ноября';
            break;
        case 'DECEMBER':
            txt = 'декабря';
            break;
    }

    return txt;
}
