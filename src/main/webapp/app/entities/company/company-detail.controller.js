(function() {
    'use strict';

    angular
        .module('recruitsmartApp')
        .controller('CompanyDetailController', CompanyDetailController);

    CompanyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity',
        'Principal', 'Company', 'JobOrder', 'HiringContact', 'Activity', 'Skill'];

    function CompanyDetailController($scope, $rootScope, $stateParams, previousState, entity,
                                     Principal, Company, JobOrder, HiringContact, Activity, Skill) {
        var vm = this;

        vm.company = entity;
        vm.previousState = previousState.name;

        getAccount();

        var unsubscribe = $rootScope.$on('recruitsmartApp:companyUpdate', function(event, result) {
            vm.company = result;
        });
        $scope.$on('$destroy', unsubscribe);

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.user = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

        vm.contactSave = function () {
            Company.update(vm.company, function (result) {
                vm.company = result;
            }, onSaveError);
            vm.contactEditable = false;
        };
        vm.cancelContactSave = function () {
            vm.contactEditable = false;
        };

        vm.addAddress = function () {
            vm.address = {
                address1: null,
                address2: null,
                city: null,
                state: null,
                zipCode: null
            };
            vm.company.addresses.push(vm.address);
        };

        vm.removeAddress = function (index) {
            vm.company.addresss.splice(index, 1);
        };

        //COMMENTS
        //================== General Comments ================
        vm.commentSave = function () {
            vm.company.companyComments.push({
                comment: vm.companyComment,
                user: vm.user
            });
            Company.update(vm.company, function (result) {
                vm.company = result;
            }, onSaveError);
            vm.commentEditable = false;
        };

        vm.cancelComment = function () {
            vm.commentEditable = false;
        };

        vm.removeComment = function (index) {
            vm.company.companyComments.splice(index, 1);
            Company.update(vm.company, onSaveSuccess, onSaveError);
        };
        //End of General Comments

        // Internal Comments
        //==================  Internal Comments ================
        vm.internalCommentSave = function () {
            vm.company.companyInternalComments.push({
                comment: vm.companyInternalComment,
                user: vm.user
            });
            Company.update(vm.company, function (result) {
                vm.company = result;
            }, onSaveError);
            vm.internalCommentEditable = false;
        };

        vm.cancelInternalComment = function () {
            vm.internalCommentEditable = false;
        };

        vm.removeInternalComment = function (index) {
            vm.company.companyInternalComments.splice(index, 1);
            Company.update(vm.company, onSaveSuccess, onSaveError);
        };
        // End of Internal Comments
        var onSaveError = function (result) {
            vm.isSaving = false;
        };
    }
})();
