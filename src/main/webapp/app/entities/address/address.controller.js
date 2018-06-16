(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('AddressController', AddressController);

  AddressController.$inject = ['Address', 'AddressSearch'];

  function AddressController(Address, AddressSearch) {

    var vm = this;

    vm.addresses = [];
    vm.clear = clear;
    vm.search = search;
    vm.loadAll = loadAll;

    loadAll();

    function loadAll() {
      Address.query(function (result) {
        vm.addresses = result;
        vm.searchQuery = null;
      });
    }

    function search() {
      if (!vm.searchQuery) {
        return vm.loadAll();
      }
      AddressSearch.query({query: vm.searchQuery}, function (result) {
        vm.addresses = result;
        vm.currentSearch = vm.searchQuery;
      });
    }

    function clear() {
      vm.searchQuery = null;
      loadAll();
    }
  }
})();
