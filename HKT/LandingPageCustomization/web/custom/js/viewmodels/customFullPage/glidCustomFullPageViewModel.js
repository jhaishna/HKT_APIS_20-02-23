define([
    'jquery',
    'knockout'
], function ($, ko) {
    function GlidCustomFullPageViewModel() {
        var self = this;
        self.title = 'Gl Code Maintenance';

        self.close = function (data, event) {
            location.hash = '';
        };
    }
    return GlidCustomFullPageViewModel;
});