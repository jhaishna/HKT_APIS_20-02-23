/**************************************************************************************
 * This is the view model that extends OOB MakePaymentViewModel.
 *
 */
define(['knockout', 'underscore', 'viewmodels/payment/MakePaymentViewModel', Registry.makePayment.bindings], function (ko, _, MakePaymentViewModel) {
    function CustomMakePayment() {
        MakePaymentViewModel.apply(this, arguments);

        var self = this;
        self.paymentChannel = ko.observable();
        var accountVM = globalAppContext.currentAccountViewModel().account();
        
        
         var setIntId = setInterval(function () {
//<input id="paymentChannel" name="payment_channel" size="14" class="required amountRightAlignText" type="text" data-bind="value:paymentChannel" style="margin-left: 20px;"/>\n\
            if (($('#makPaymentCommonUIForm').is(':visible'))) {
              $('#makPaymentCommonUIForm').append('<div style="display: flex;position: absolute; margin-top: 10px;left: 10px;"><label for="payment_amount">Payment Channel</label>\n\
<select id="paymentChannel" name="payment_channel" data-bind="value:paymentChannel"></select></div>');
 
//$('#makPaymentCommonUIForm').append('<div style="display: flex;position: absolute; margin-top: 10px;left: 10px;">\n\
//<label for="payment_amount">Payment Channel</label>\n\
//<select data-bind="options: self.paymentChannelsArray, optionsText: 'channelName', optionsCaption: 'Select...', optionsValue: 'channelId', value:paymentChannel"></div>');

               clearInterval(setIntId);
               var wrapper = $("#paymentChannel");
               ko.cleanNode(wrapper);
               ko.applyBindings(self, wrapper[0]);
               $("#makPaymentCommonUIForm").css("margin-bottom", "25px");
                
                self.getViewData(); 
                
            }
        }, 500);
        
        // Start
       /**
                         * AJAX call to return the profile data
                         * 
                         * @returns profile data ($.ajax)
                         */
                        self.getViewData = function () {
                            var currentAccount = globalAppContext.currentAccountViewModel().account();
                            var data = {
                                searchTemplateName : ko.observable("PaymentChannelSearch"),
                                criterias : ko.observableArray([{"field":"Domain","value":"Payment Channel"}])
                            };
                            return  $.ajax({
                                type: "POST",
                                url: baseURL + "/private/search",
                                contentType: "application/json; charset=utf-8",
                                data: ko.toJSON(data),
                                processData: false,
                                beforeSend: function (xhr) {
                                    util.setRequestHeader(xhr)
                                },
                                dataType: "json"
                            }).done(function (response) {
                                  var paymentChannels = new Array();
                                  paymentChannels.push(["Select a PaymentChannel", "0"]);
                                  var tempArray = new Array();
                                    for (var i = 0; i < response.results.length; i++) {
                                         for (var j = 0; j < response.results[i].column.length; j++) {
                                            tempArray.push(response.results[i].column[j].value);
                                            if(j === response.results[i].column.length-1) {
                                                paymentChannels.push([tempArray[0],tempArray[1]]);
                                                tempArray = new Array();
                                            }
                                         }
                                    }
                                    console.log(paymentChannels);
                                   
                                  var options = '';

                                    for (var i = 0; i < paymentChannels.length; i++) {
                                       options += '<option value="' + paymentChannels[i][1]+ '">' + paymentChannels[i][0] + '</option>';
                                    }
                                    $("#paymentChannel").html(options);
                                    
                                   
                                    
                            }).fail(function (errorResponse) {
                                self.handleError(errorResponse);
                            });
                        };
                       //  self.getViewData();
                         
                        // ko.applyBindings(self.paymentChannelsArray);
                        // End--
        //waiting for make Payment fields to be visible and
        //adding custom field and binding it with self.
       

        //before calling REST we are creating payment object with
        //custom field payment channel in external field of payment object
        self.toJSON = function () {
            var paymentObj = self.currentRenderedPaymentViewModel.toJSON();
            paymentObj.selectedBillUnit = self.currentBillUnit();
            paymentObj.itemPaymentRef = accountVM.id();
            paymentObj.amount = util.unformatAmount(self.amount());
            paymentObj.currency = accountVM.currency();
            paymentObj.notes = self.getNotesDetail();

            paymentObj.extension= {paymentChannel : self.paymentChannel()};
            return paymentObj;
        };
    }

    CustomMakePayment.prototype = new MakePaymentViewModel();
    return CustomMakePayment;
});