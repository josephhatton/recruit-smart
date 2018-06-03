(function() {
    'use strict';

    angular
        .module('recruitsmartApp')
        .controller('CompanyDetailController', CompanyDetailController);

    CompanyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity',
        'Principal', 'Company', 'Address', 'JobOrder', 'HiringContact', 'Activity', 'Skill'];

    function CompanyDetailController($scope, $rootScope, $stateParams, previousState, entity,
                                     Principal, Company, Address, JobOrder, HiringContact, Activity, Skill) {
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

        vm.cancelInternalCommentSave = function () {
            vm.internalCommentEditable = false;
            vm.load(vm.company.id); //reload company data
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
