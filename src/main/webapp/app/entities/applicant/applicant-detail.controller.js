(function() {
    'use strict';

    angular
        .module('recruitsmartApp')
        .controller('ApplicantDetailController', ApplicantDetailController);

    ApplicantDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Applicant', 'Address', 'ApplicantStatus', 'ApplicantComment', 'Skill', 'WorkHistory', 'ApplicantInternalComment', 'WorkStatus', 'Activity'];

    function ApplicantDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Applicant, Address, ApplicantStatus, ApplicantComment, Skill, WorkHistory, ApplicantInternalComment, WorkStatus, Activity) {
        var vm = this;

        vm.applicant = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('recruitsmartApp:applicantUpdate', function(event, result) {
            vm.applicant = result;
        });
        $scope.$on('$destroy', unsubscribe);

        vm.contactSave = function () {
            vm.isSaving = true;
            Applicant.update(vm.applicant, onSaveSuccess, onSaveError);
            vm.contactEditable = false;
            vm.compEdit = false;
            vm.addNewActivity = false;
            vm.resumeEditable = false;
        };

        vm.cancelContactSave = function () {
            vm.contactEditable = false;
            vm.compEdit = false;
            vm.resumeEditable = false;
            vm.load(vm.applicant.id); //reload applicant data
        };

        vm.skills = Skill.query();
        vm.addAddress = function () {
            vm.applicant.addresss.push({
                address1: null,
                address2: null,
                city: null,
                state: null,
                zipCode: null,
                id: null
            });
        };

        vm.removeAddress = function (index) {
            vm.applicant.addresss.splice(index, 1);
        };

        vm.addSkill = function () {
            //If new skill is an object then we know the skill exists in database
            if (vm.newSkill !== null && typeof vm.newSkill === 'object') {
                vm.applicant.skills.push(vm.newSkill);
                Applicant.update(vm.applicant, onSaveSuccess, onSaveError);
            } else if (vm.newSkill !== null) {
                //If it's not, then add it to DB, then on completion update applicant
                Skill.save({
                    id: null,
                    name: vm.newSkill
                }, function (result) {
                    vm.applicant.skills.push(result);
                    Applicant.update(vm.applicant, onSaveSuccess);
                }, function () {
                    //failed to add skill to DB
                    vm.applicant.skills.pop();
                    onSaveError();
                });
            }
            vm.newSkill = '';
        };

        vm.deleteSkill = function (index) {
            vm.applicant.skills.splice(index, 1);
            Applicant.update(vm.applicant, onSaveSuccess, onSaveError);
        };

        //COMMENTS
        //General Comments
        vm.saveComment = function () {
            if (vm.newApplicantComment !== null) {
                vm.isSaving = true;
                vm.applicant.applicantCommentss.push({
                    id: null,
                    description: vm.newApplicantComment
                });
                Applicant.update(vm.applicant, function (result) {
                    vm.applicant = result;
                }, onSaveError);
            }
            vm.newApplicantComment = '';
            vm.editableComment = false;
        };

        vm.cancelComment = function () {
            vm.editableComment = false;
            vm.compEdit = false;
            vm.load(vm.applicant.id); //reload applicant data
        };

        vm.removeGenComments = function (index) {
            vm.applicant.applicantCommentss.splice(index, 1);
            Applicant.update(vm.applicant, onSaveSuccess, onSaveError);
        };
        //End of General Comments

        // Internal Comments

        vm.internalCommentsSave = function () {
            if (vm.newApplicantInternalComment !== null) {
                vm.isSaving = true;
                vm.applicant.applicantInternalCommentss.push({
                    id: null,
                    description: vm.newApplicantInternalComment
                });
                Applicant.update(vm.applicant, function (result) {
                    vm.applicant = result;
                }, onSaveError);
            }
            vm.newApplicantInternalComment = '';
            vm.internalCommentsEditable = false;
        };

        vm.cancelInternalCommentsSave = function () {
            vm.internalCommentsEditable = false;
            vm.load(vm.applicant.id); //reload applicant data
        };

        vm.removeInternalComments = function (index) {
            vm.applicant.applicantInternalCommentss.splice(index, 1);
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
