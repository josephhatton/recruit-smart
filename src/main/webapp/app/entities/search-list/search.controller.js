(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('SearchController', SearchController)


  SearchController.$inject = ['$scope', '$state', '$uibModal', '$filter', 'SearchList', 'Account', 'User'];

  function SearchController($scope, $state, $uibModal, $filter, SearchList, Account, User) {
    var vm = this;

    //query boxes
    vm.someQs = ['', '', '', ''];
    vm.allQs = ['', '', '', ''];
    vm.noneQs = ['', '', '', ''];
    //arrays of indexes for filled out query boxes
    vm.someQsIndex = [];
    vm.allQsIndex = [];
    vm.noneQsIndex = [];
    //main query
    vm.searchList = {
      name: "New Untitled",
      type: "APPLICANT",
      query: "",
      id: null
    };

    vm.view_tab = 'searchTab';
    vm.actions = ["email All", "email Selected", "text All", "text Selected", "add All to Hot List", "add Selected to Hot List", "Actions"];
    vm.searchFrom = [{
      name: "Skills"
    }, {
      name: "Resume"
    }, {
      name: "Notes"
    }, {
      name: "Attachments"
    }, {
      name: "Activities"
    }];
    //object for geo search data
    vm.geo = {};
    vm.geo.state = '';
    vm.states = ('AL AK AZ AR CA CO CT DE FL GA HI ID IL IN IA KS KY LA ME MD MA MI MN MS ' +
    'MO MT NE NV NH NJ NM NY NC ND OH OK OR PA RI SC SD TN TX UT VT VA WA WV WI ' +
    'WY').split(' ').map(function (state) {
      return {abbrev: state};
    });

    //load Entity
    vm.loadAll = function () {
      var loggedInUser = Account.get();
      loggedInUser.$promise.then(function () {
        vm.searchList.user = User.get({login: loggedInUser.login});
      });
      SearchList.query(function (result) {
        vm.searchLists = result;
        vm.searchLists.unshift(vm.searchList);
      });

      vm.typeCheck.query[vm.searchList.type].query(function (result) {
        vm.applicants = [];
        vm.companys = [];
        vm.jobOrders = [];
        vm.hiringContacts = [];
        //sets one of the above arrays based on type
        vm[vm.typeCheck.whatToChange[vm.searchList.type] + 's'] = result;
      });
    };
    vm.loadAll();

    vm.changeTab = function (tab, index) {
      vm.view_tab = tab;
      vm.entityIndex = index;
      //sets vm.applicant or vm.company ... etc ( single entity )
      vm[vm.typeCheck.whatToChange[vm.searchList.type]] = vm[vm.typeCheck.whatToChange[vm.searchList.type] + 's'][vm.entityIndex];
    }

    vm.searchListChange = function () {
      vm.view_tab = 'searchTab';

      if (vm.searchList.query && vm.searchList.query !== "") {
        vm.someQs = [];
        vm.allQs = [];
        vm.noneQs = [];
        queryDeconcatonator(vm.searchList.query);
      }
      vm.search();
    }

    vm.typeChange = function () {
      vm.loadAll();
    }

    vm.toggleGeoCollapse = function (geoSection) {
      if (vm.geoCollapse === geoSection) {
        vm.geoCollapse = "nunya";
      } else {
        vm.geoCollapse = geoSection;
      }
    }

    vm.actionChange = function () {
      var hotlist = {
        type: vm.searchList.type,
        name: vm.searchList.name
      }
      if (vm.selectedAction === "add All to Hot List") {
        hotlist[vm.typeCheck.whatToChange[vm.searchList.type] + 's'] = vm[vm.typeCheck.whatToChange[vm.searchList.type] + 's'];
        vm.openHotList(hotlist);

      } else if (vm.selectedAction === "add Selected to Hot List") {
        hotlist[vm.typeCheck.whatToChange[vm.searchList.type] + 's'] = $filter('filter')(vm[vm.typeCheck.whatToChange[vm.searchList.type] + 's'], {checked: true});
        vm.openHotList(hotlist);

      }
      vm.selectedAction = "";
    }

    vm.toggleSearchFromAll = function () {
      if (vm.selectedAll) {
        vm.selectedAll = false;
      } else {
        vm.selectedAll = true;
      }
      angular.forEach(vm.searchFrom, function (item) {
        item.Selected = vm.selectedAll;
      });
    }

    //search function
    vm.search = function () {
      console.log('Search Func called'); //ph
      //build main query from boxes if nothing is there otherwise use it
      if (!vm.searchList.query || vm.searchList.query === "") {
        queryConcatenator();
      }

      //If query is still empty just load everything
      if (!vm.searchList.query || vm.searchList.query === "") {
        return vm.loadAll(); //return ends the search function
      }

      //save search
      if (vm.searchList.id) {
        SearchList.update(vm.searchList);
      } else {
        SearchList.save(vm.searchList);
      }

      vm.typeCheck.search[vm.searchList.type].query({query: vm.searchList.query}, function (result) {
        console.log('Searching.....'); //ph
        vm.applicants = [];
        vm.companys = [];
        vm.jobOrders = [];
        vm.hiringContacts = [];

        vm[vm.typeCheck.whatToChange[vm.searchList.type] + 's'] = result;
        console.log(result); //ph
        vm.someQsIndex = [];
        vm.allQsIndex = [];
        vm.noneQsIndex = [];
      });

    };

    var queryConcatenator = function () {
      //refresh main query
      vm.searchList.query = "";
      //Check what boxes are filled if any
      for (var i = 0; i < vm.allQs.length; i++) {
        if (vm.allQs[i] !== '') {
          vm.allQsIndex.push(i);
        }
      }
      for (var i = 0; i < vm.someQs.length; i++) {
        if (vm.someQs[i] !== '') {
          vm.someQsIndex.push(i);
        }
      }
      for (var i = 0; i < vm.noneQs.length; i++) {
        if (vm.noneQs[i] !== '') {
          vm.noneQsIndex.push(i);
        }
      }
      //check if any ALL boxes are filled
      if (vm.allQsIndex.length > 0) {
        //wrap those strings in "("")" and add "AND" btw them
        vm.searchList.query += "(";
        for (var i = 0; i < vm.allQsIndex.length; i++) {
          vm.searchList.query += " " + vm.allQs[vm.allQsIndex[i]];
          //check if we are at last index
          if (i !== vm.allQsIndex.length - 1) {
            vm.searchList.query += " AND";
          } else {
            vm.searchList.query += " )";
          }
        }

      }
      //check if any ALL boxes are filled AND any SOME boxes
      if (vm.allQsIndex.length > 0 && vm.someQsIndex.length > 0) {
        //if true add " AND "
        vm.searchList.query += " AND ";
      }
      //check if any SOME boxes filled
      if (vm.someQsIndex.length > 0) {
        //wrap in "("")" and add "OR" btw them
        vm.searchList.query += "(";
        for (var i = 0; i < vm.someQsIndex.length; i++) {
          vm.searchList.query += " " + vm.someQs[vm.someQsIndex[i]];
          //check if we are at last index
          if (i !== vm.someQsIndex.length - 1) {
            vm.searchList.query += " OR";
          } else {
            vm.searchList.query += " )";
          }
        }
      }
      //check if any ALL boxes are filled OR any SOME boxes AND any NONE boxes are filled
      if ((vm.allQsIndex.length > 0 || vm.someQsIndex.length > 0) && vm.noneQsIndex.length > 0) {
        //if true add " AND "
        vm.searchList.query += " AND ";
      }
      //check if any NONE boxes are filled
      if (vm.noneQsIndex.length > 0) {
        //wrap in "NOT ("")" and add "OR" btw them
        vm.searchList.query += "NOT (";
        for (var i = 0; i < vm.noneQsIndex.length; i++) {
          vm.searchList.query += " " + vm.noneQs[vm.noneQsIndex[i]];
          //check if we are at last index
          if (i !== vm.noneQsIndex.length - 1) {
            vm.searchList.query += " OR";
          } else {
            vm.searchList.query += " )";
          }
        }
      }
    }

    var queryDeconcatonator = function (query) {
      // inSomething refers to if we are in between parenthesis while we are looping through the query
      var inSomething = false;
      var endIndex = query.length - 1;

      //searching through the string starting from the right
      for (var i = query.length - 1; i >= 0; i--) {
        if (inSomething) {
          //if we are in something, ie. left of a ')' char, check if we are at the end of it, ie a '('
          if (query[i] === '(') {
            inSomething = false;
            //check if it is preceeded by a 'NOT '
            if (query.slice(i - 4, i) === 'NOT ') {
              //if it is we know it will fill the vm.noneQs
              fillNunya(query.slice(i + 2, endIndex));
            } else {
              //else it will either fill the vm.someQs or vm.allQs
              fillAllyaOrSumYa(query.slice(i + 2, endIndex));
            }
          }
        }
        if (query[i] === ')') {
          //we entered some that will represent vm.allQs, vm.someQs or vm.noneQs
          inSomething = true;
          //save index for future splicing
          endIndex = i - 1;
        }
      }
      fillEmptys(vm.allQs);
      fillEmptys(vm.someQs);
      fillEmptys(vm.noneQs);
    }

    var fillNunya = function (str) {
      //split string str into array of words that are seperated by a space
      var tempArray = str.split(" ");
      vm.noneQs = [];
      for (var i = 0; i < tempArray.length; i++) {
        if (tempArray[i] !== 'AND') {
          vm.noneQs.push(tempArray[i]);
        }
      }
    }

    var fillAllyaOrSumYa = function (str) {
      //split string str into array of words that are seperated by a space
      var tempArray = str.split(" ");

      //temp array to add to til we find out if we are adding to vm.allQs or vm.someQs
      var tempYa = [];
      var youAreHere = "?";

      for (var i = 0; i < tempArray.length; i++) {
        if (tempArray[i] === 'AND') {
          youAreHere = "allYa";
        } else if (tempArray[i] === 'OR') {
          youAreHere = "sumYa";
        } else {
          tempYa.push(tempArray[i]);
        }
      }

      //set vm.allQs or vm.someQs with tempYa depending on which it was
      if (youAreHere === "allYa") {
        vm.allQs = tempYa.slice(0);
      } else if (youAreHere === "sumYa") {
        vm.someQs = tempYa.slice(0);
      } else {
        //if there was only one word you wont know if it was initially part of allQs or someQs
        //arbitrarily places it one or the other as long as nothing else is there
        if (vm.someQs.length < 1) {
          vm.someQs = tempYa.slice(0);
        } else if (vm.allQs.length < 1) {
          vm.allQs = tempYa.slice(0);
        }
      }
    }

    var fillEmptys = function (arya) {
      if (arya.length < 4) {
        for (var i = arya.length; i < 4; i++) {
          arya.push(' ');
        }
      }
    }

    //opens hot list modal
    vm.openHotList = function (hotlist) {

      var modalInstance = $uibModal.open({
        animation: true,
        templateUrl: 'app/home/hot-list-modal.html',
        controller: 'HotListModalController',
        controllerAs: 'vm',
        size: 'sm',
        resolve: {
          entity: function () {
            return hotlist;
          }
        }
      });

      //modalInstance.result.then(function (newList) {
      //    vm.account.hotLists.push(newList);
      //});

    };
  }

})();
