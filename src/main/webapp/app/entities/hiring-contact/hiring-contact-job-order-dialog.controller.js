(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('HiringContactJobOrderDialogController', HiringContactJobOrderDialogController);

  HiringContactJobOrderDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'HiringContact', 'JobOrder', 'Activity'];

  function HiringContactJobOrderDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, HiringContact, JobOrder, Activity) {
    var vm = this;

    vm.hiringContact = entity;
    vm.clear = clear;
    vm.jobOrders = JobOrder.query();

    $timeout(function () {
    });

    function clear() {
      $uibModalInstance.dismiss('cancel');
    }

    vm.changedJobOrder = function (checked,job) {
      if(checked) {
        vm.hiringContact.jobOrders.push(job)
      } else {
        for (var i = 0; i < vm.hiringContact.jobOrders.length; i++) {
          if (vm.hiringContact.jobOrders[i].id === job.id) {
            vm.hiringContact.jobOrders.splice(i, 1);
            return
          }
        }
      }
    }

    vm.save = function () {
      HiringContact.update(vm.hiringContact, onSaveSuccess, onSaveError);
    }

    function onSaveSuccess(result) {
      $scope.$emit('recruitsmartApp:hiringContactUpdate', result);
      vm.hiringContact = result
      $uibModalInstance.close(result);
    }

    function onSaveError() {
    }
  }
})();
