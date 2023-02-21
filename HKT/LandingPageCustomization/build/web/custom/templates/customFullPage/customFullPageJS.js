define([
    'jquery',
    'knockout'
], function ($, ko) {
    function CustomFullPageViewModel() {
        var self = this;
        self.title = 'Adjustment Code Enquiry';

        self.close = function (data, event) {
            location.hash = '';
        };
    }
    return CustomFullPageViewModel;
});