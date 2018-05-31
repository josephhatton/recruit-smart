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

    }
})();
