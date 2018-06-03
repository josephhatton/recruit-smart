(function() {
    'use strict';

    angular
        .module('recruitsmartApp')
        .controller('ApplicantDetailController', ApplicantDetailController);

    ApplicantDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity',
        'Principal', 'Applicant', 'Address', 'ApplicantStatus', 'Skill', 'WorkHistory', 'WorkStatus', 'Activity'];

    function ApplicantDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity,
                                       Principal, Applicant, Address, ApplicantStatus, Skill, WorkHistory, WorkStatus, Activity) {
        var vm = this;

        vm.applicant = entity;
        vm.user = null;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        getAccount();
        var unsubscribe = $rootScope.$on('recruitsmartApp:applicantUpdate', function(event, result) {
            vm.applicant = result;
        });
        $scope.$on('$destroy', unsubscribe);

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.user = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

        vm.contactSave = function () {
            vm.isSaving = true;
            Applicant.update(vm.applicant, onSaveSuccess, onSaveError);
            vm.contactEditable = false;
            vm.compensationEditable = false;
            vm.addNewActivity = false;
            vm.resumeEditable = false;
        };

        vm.cancelContactSave = function () {
            vm.contactEditable = false;
            vm.compensationEditable = false;
            vm.resumeEditable = false;
        };

        vm.skills = Skill.query();

        vm.addAddress = function () {
            vm.address = {
                address1: null,
                address2: null,
                city: null,
                state: null,
                zipCode: null
            };
            vm.applicant.addresses.push(vm.address);
        };

        vm.removeAddress = function (index) {
            vm.applicant.addresses.splice(index, 1);
        };

        vm.addSkill = function () {
            //If it's not, then add it to DB, then on completion update applicant
            Skill.update({
                name: vm.newSkill
            }, function (result) {
                vm.applicant.skills.push(result);
                Applicant.update(vm.applicant, onSaveSuccess);
            }, function () {
                //failed to add skill to DB
                vm.applicant.skills.pop();
                onSaveError();
            });
        };

        vm.deleteSkill = function (index) {
            vm.applicant.skills.splice(index, 1);
            Applicant.update(vm.applicant, onSaveSuccess, onSaveError);
        };

        //COMMENTS
        //-------------------------- GENERAL COMMENTS -------------------------------
        vm.saveComment = function () {
            vm.applicant.applicantComments.push({
                comment: vm.applicantComment,
                user: vm.user
            });
            Applicant.update(vm.applicant, function (result) {
                vm.applicant = result;
            }, onSaveError);
            vm.editableComment = false;
        };

        vm.cancelComment = function () {
            vm.editableComment = false;
        };

        vm.removeGenComments = function (index) {
            vm.applicant.applicantComments.splice(index, 1);
            Applicant.update(vm.applicant, onSaveSuccess, onSaveError);
        };
        //End of General Comments

        // Internal Comments
        //-------------------------- INTERNAL COMMENTS -------------------------------
        vm.internalCommentsSave = function () {
            vm.applicant.applicantInternalComments.push({
                comment: vm.newApplicantInternalComment,
                user: vm.user
            });
            Applicant.update(vm.applicant, function (result) {
                vm.applicant = result;
            }, onSaveError);
            vm.internalCommentsEditable = false;
        };

        vm.cancelInternalCommentsSave = function () {
            vm.internalCommentsEditable = false;
        };

        vm.removeInternalComments = function (index) {
            vm.applicant.applicantInternalComments.splice(index, 1);
            Applicant.update(vm.applicant, onSaveSuccess, onSaveError);
        };
        // End of Internal Comments

        //END OF COMMENTS

        var onSaveSuccess = function (result) {
            $scope.$emit('recruitsmartApp:addressUpdate', result);
            vm.isSaving = false;
        };

        var onSaveError = function (result) {
            vm.isSaving = false;
        };

    }
})();
