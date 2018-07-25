(function () {
  'use strict';

  angular.module('recruitsmartApp').controller('CalculatorModalController',
    CalculatorModalController);

  CalculatorModalController.$inject = ['$stateParams',
    '$uibModalInstance'];

  function CalculatorModalController($stateParams, $uibModalInstance) {
    var vm = this;

    vm.burden = .16;

    vm.calculate = function () {

      vm.payRate = parseInt(vm.payRate);

      vm.burdenPayRate = vm.payRate + vm.payRate * vm.burden;

      vm.pto = (vm.payRate * 80) / 2080;

      vm.loadedCost = vm.payRate + vm.pto;

      vm.gp = parseInt(vm.billRate) - vm.loadedCost;

      if (!vm.ptoChecked) {
        vm.pto = '';
      }
    };

    vm.clear = function () {
      $uibModalInstance.dismiss('cancel');
    };
  }
})();
