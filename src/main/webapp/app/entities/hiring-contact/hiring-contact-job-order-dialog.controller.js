(function() {
    'use strict';

    angular
        .module('recruitsmartApp')
        .controller('HiringContactJobOrderDialogController', HiringContactJobOrderDialogController);

    HiringContactJobOrderDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'HiringContact', 'JobOrder', 'Activity'];

    function HiringContactJobOrderDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, HiringContact, JobOrder, Activity) {
        var vm = this;

        vm.hiringContact = entity;
        vm.clear = clear;
        vm.jobOrders = JobOrder.query();

        $timeout(function (){
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.hiringContact.id !== null) {
                HiringContact.update(vm.hiringContact, onSaveSuccess, onSaveError);
            } else {
                HiringContact.save(vm.hiringContact, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('recruitsmartApp:hiringContactUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
